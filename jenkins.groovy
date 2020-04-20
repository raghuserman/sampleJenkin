pipeline {
  agent any
  
  stages {
   
    stage('Fetch the package from Git') {
      steps {
        echo "Pull the code from Git"
      }
    }  
    stage('Deployment') {
      parallel {
        stage('Staging') {
          when {
            branch 'staging'
          }
          steps {
            withAWS(region:'us-east-1',credentials:'aws-cloud-user') {
		    s3Upload(bucket: 'rajeshbala', workingDir:'.', includePathPattern:'**/*');
            }
            mail(subject: 'Staging Build', body: 'New Deployment to Staging', to: 'jenkins-mailing-list@mail.com')
          }
        }
        stage('Production') {
          when {
            branch 'master'
          }
          steps {
            withAWS(region:'us-east-1',credentials:'aws-cloud-user') {
              s3Upload(bucket: 'rajeshbala', workingDir:'.', includePathPattern:'outboundFolder');
            }
            mail(subject: 'Production Build', body: 'New Deployment to Production', to: 'rajesh.bala@outlook.com')
          }
        }
      }
    }
  }
}
