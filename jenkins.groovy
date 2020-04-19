pipeline
{
    agent
    {
    node {
        label 'windows'
      }
    }
    stages
    {
        stage('Start the Build')
        {
         steps
            {
           echo "start the build"
            }
        }
        stage('Update the Build')
         {
         steps
            {
           echo "Update the build"
           sh"""
                cd /"${workspace}"
                echo "Test Content" > test.txt
            """
            }
         }
        stage('Push to Master...')
          steps
          {
            {
           echo "Push the code to Master"    
           sh"""
                set +x
                git config --global user.name 
                git config --global user.email
                set -x
                git remote set-url origin https://github.com/santhoshvelusamy/Run-Apache.git
                git status
                git add .
                git commit -m "Pushing" | true
                git push -u origin master
                """ 
            }
        }
    }
}
