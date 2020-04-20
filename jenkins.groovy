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
            branch 'branch'
          }
          steps {
            withAWS(region:'us-east-1',credentials:'aws-cloud-user') {
		    s3Upload(bucket: 'rajeshbala', workingDir:'.', includePathPattern:'html/');
            }
            
          }
        }
        stage('Production') {
          when {
            branch 'master'
          }
          steps {
            withAWS(region:'us-east-1',credentials:'aws-cloud-user') {
            s3Upload(bucket: 'rajeshbala', workingDir:'.', includePathPattern:'html/');
	    echo "Sucessfully uploaded the Html to S3 web hosting"
            }
           
          }
        }
      }
    }
  }
}
