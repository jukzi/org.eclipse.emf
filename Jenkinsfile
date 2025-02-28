def targetPlatformToJavaVersionMap = [
  '2023-06' : '17',
  '2023-03' : '11',
  '2022-12' : '11',
  '2022-09' : '11',
  '2022-06' : '11',
  '2022-03' : '11',
  '2021-12' : '11',
  '2021-09' : '11',
  '2021-06' : '11',
  '2021-03' : '11',
  '2020-12' : '11',
  '2020-09' : '11',
  '2020-06' : '1.8',
  '2020-03' : '1.8',
  '2019-12' : '1.8',
  '2019-09' : '1.8',
  '2019-06' : '1.8',
  '2019-03' : '1.8',
  '2018-12' : '1.8',
  '2018-09' : '1.8',
  'photon'  : '1.8',
  'oxygen'  : '1.8',
  'neon'    : '1.8',
  'mars'    : '1.8',
  'luna'    : '1.8',
  'kepler'  : '1.8'
]

def targetPlatforms = targetPlatformToJavaVersionMap.keySet() as List

pipeline {
  agent {
    label 'centos-latest'
  }

  options {
    buildDiscarder(logRotator(numToKeepStr: '10'))
    disableConcurrentBuilds()
    skipDefaultCheckout false
  }

  tools {
    maven 'apache-maven-latest'
    jdk 'temurin-jdk17-latest'
  }

  environment {
    PUBLISH_LOCATION = 'builds'
    BUILD_TIMESTAMP = sh(returnStdout: true, script: 'date +%Y%m%d%H%M').trim()
    CHECKOUT = 'false'
  }

  parameters {
    choice(
      name: 'BUILD_TYPE',
      choices: ['nightly', 'milestone', 'release'],
      description: '''
        Choose the type of build.
        Note that a release build will not promote the build, but rather will promote the most recent milestone build.
        '''
    )

    choice(
      name: 'TARGET_PLATFORM',
      choices: targetPlatforms,
      description: '''
        Choose the named target platform against which to compile and test.
        This is relevant only for nightly and milestone builds.
        '''
    )

    booleanParam(
      name: 'ECLIPSE_SIGN',
      defaultValue: false,
      description: '''
        Choose whether or not the bundles will be signed.
        This is relevant only for nightly and milestone builds.
      '''
    )
 
    booleanParam(
      name: 'PROMOTE',
      defaultValue: false,
      description: 'Whether to promote the build to the download server.'
    )

    booleanParam(
      name: 'ARCHIVE',
      defaultValue: false,
      description: 'Whether to archive the workspace.'
    )
  }

  stages {
    stage('Display Parameters') {
      steps {
        echo "BUILD_TIMESTAMP=${env.BUILD_TIMESTAMP}"
        echo "BUILD_TYPE=${params.BUILD_TYPE}"
        echo "TARGET_PLATFORM=${params.TARGET_PLATFORM}"
        echo "JAVA_VERSION=" + targetPlatformToJavaVersionMap[params.TARGET_PLATFORM]
        echo "ECLIPSE_SIGN=${params.ECLIPSE_SIGN}"
        echo "PROMOTE=${params.PROMOTE}"
        echo "ARCHIVE=${params.ARCHIVE}"
        script {
          env.BUILD_TYPE = params.BUILD_TYPE
          env.TARGET_PLATFORM = params.TARGET_PLATFORM
          env.JAVA_VERSION = targetPlatformToJavaVersionMap[params.TARGET_PLATFORM]
          env.ECLIPSE_SIGN = params.ECLIPSE_SIGN
          env.PROMOTE = params.PROMOTE && env.ECLIPSE_SIGN
        }
      }
    }

    stage('Git Checkout') {
      when {
        environment name: 'CHECKOUT', value: 'true'
      }
      steps {
        script {
          def gitVariables = checkout(
            poll: false,
            scm: [
              $class: 'GitSCM',
              branches: [[name: '*' + '/master']],
              doGenerateSubmoduleConfigurations: false,
              submoduleCfg: [],
              userRemoteConfigs: [[url: 'https://github.com/eclipse-emf/emf.git']]
            ]
          )

          echo "$gitVariables"
          env.GIT_COMMIT = gitVariables.GIT_COMMIT
        }
      }
    }

    stage('Build') {
      steps {
        sshagent(['projects-storage.eclipse.org-bot-ssh']) {
          dir('.') {
            sh '''
              if [[ $PROMOTE == false ]]; then
                promotion_argument='-Dpromote=false -Dorg.eclipse.justj.p2.manager.args='
              fi
              mvn  \
                --fail-at-end \
                --no-transfer-progress \
                $promotion_argument \
                -Dmaven.artifact.threads=16 \
                -Dbuild.id=${BUILD_TIMESTAMP} \
                -Dcommit.id=$GIT_COMMIT \
                -DECLIPSE_SIGN=${ECLIPSE_SIGN} \
                -Dtarget-platform=${TARGET_PLATFORM} \
                -DjavaVersion=${JAVA_VERSION} \
                -Dbuild.type=$BUILD_TYPE \
                -Dorg.eclipse.justj.p2.manager.build.url=$JOB_URL \
                -Dorg.eclipse.justj.p2.manager.relative=$PUBLISH_LOCATION \
                clean \
                verify
              '''
          }
        }
      }
    }

    stage('Archive Results') {
      when {
        expression {
          params.ARCHIVE
        }
      }
      steps {
        archiveArtifacts '**'
      }
    }
  }

  post {
    failure {
      mail to: 'ed.merks@gmail.com',
      subject: "[EMF CI] Build Failure ${currentBuild.fullDisplayName}",
      mimeType: 'text/html',
      body: "Project: ${env.JOB_NAME}<br/>Build Number: ${env.BUILD_NUMBER}<br/>Build URL: ${env.BUILD_URL}<br/>Console: ${env.BUILD_URL}/console"
      archiveArtifacts allowEmptyArchive: true, artifacts: '**'
    }

    fixed {
      mail to: 'ed.merks@gmail.com',
      subject: "[EMF CI] Back to normal ${currentBuild.fullDisplayName}",
      mimeType: 'text/html',
      body: "Project: ${env.JOB_NAME}<br/>Build Number: ${env.BUILD_NUMBER}<br/>Build URL: ${env.BUILD_URL}<br/>Console: ${env.BUILD_URL}/console"
    }

    always {
      junit '**/target/surefire-reports/TEST-*.xml'
    }

    cleanup {
      deleteDir()
    }
  }
}