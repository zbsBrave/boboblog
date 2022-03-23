pipeline {
    agent any

    //自定义变量
    environment {
        //远程服务目录。可以通过${JOB_NAME}获取项目名称
        publish_home = '/home/dev/blog'
    }

    stages {
        //输出构建信息
        stage("Build messages") {
            steps {
                echo "----- 准备构建项目，当前目录："
                sh "pwd"
            }
        }

        stage("打包") {
            steps {
                echo "----- 2，打包"
                //nacos打包可以参考官方文档。-Drat.skip=true ： 跳过 apache rat 检查
                sh "mvn -Dmaven.test.skip=true clean package -U"
            }
        }
        
        stage("发布") {
            steps {
                echo "----- 3，发布"
                //sh "rm -rf ${publish_home}"
                //sh "mkdir -p ${publish_home}/"
                sh "ssh ubuntu@localhost 'rm -rf ${publish_home}; mkdir -p ${publish_home}' "
                
                //将jar包复制到发布目录。 blog-server.jar这个jar包名是在pom文件中指定的
                sh "scp target/blog-server.jar ubuntu@localhost:${publish_home}"
                //复制执行文件
                sh "scp blog.sh ubuntu@localhost:${publish_home}"
            }
        }

        stage("部署") {
            steps {
                echo "----- 4，部署"
                //执行sh文件
                sh "ssh ubuntu@localhost 'cd ${publish_home}; sh ${publish_home}/blog.sh' "
            }
        }


    }
}