// Generated on 2015-09-15 using
// generator-webapp 1.0.1
'use strict';
var webpack = require('webpack');
var commonPlugins =  require("webpack/lib/optimize/CommonsChunkPlugin");
var isFunction = require('lodash.isfunction');
module.exports = function (grunt) {
  require('load-grunt-tasks')(grunt,{
    pattern: 'grunt-*',
    config: 'package.json'
  });
  // Time how long tasks take. Can help when optimizing build times
  require('time-grunt')(grunt);
  //var $ = require('expose?jQuery!../../scripts/components/jquery');
  // Automatically load required grunt tasks
  //require('jit-grunt')(grunt, {
  //    useminPrepare: 'grunt-usemin'
  //});

  // Configurable paths
  var config = {
    app: 'WebRoot',
    dist: 'dest'
  };
  // Define the configuration for all the tasks
  grunt.initConfig({

    // Project settings
    config: config,

    // Watches files for changes and runs tasks based on the changed files
    watch: {
      bower: {
        files: ['bower.json'],
        tasks: ['wiredep']
      },
      webpack: {
        files: ['<%= config.app %>/scripts/server/js_ForDev/**/*.js'],
        //files: ['<%= config.app %>/scripts/server/js_ForDev/**/*.js','<%= config.app %>/scripts/activityList/js_ForDev/**/*.js'],
        tasks: ['webpack']
      },
      gruntfile: {
        files: ['Gruntfile.js']
      },
      sass: {
        files: ['<%= config.app %>/sass/pages/server/*.{scss,sass}'],
<<<<<<< HEAD
        tasks: ['newer:clean:server','newer:sass:server','newer:autoprefixer']
=======
        //files: ['<%= config.app %>/sass/pages/activityList/*.{scss,sass}'],
        tasks: ['clean:server','sass:server','autoprefixer']
>>>>>>> feature/webpack
      }
    },
    babel: {
      options: {
        sourceMap: true
      },
      dist: {
        files: {
<<<<<<< HEAD
          '/scripts/server/**/*.js':'<%= config.app %>/scripts/server/**/*.js'
=======
          '/scripts/server/**/*.js':'<%= config.app %>/scripts/server/**/*.js',
          //'/scripts/activityList/**/*.js':'<%= config.app %>/scripts/activityList/**/*.js'
>>>>>>> feature/webpack
        }
      }
    },
    browserSync: {
      options: {
        notify: false,
        background: true
      },
      livereload: {
        options: {
          files: [
            '<%= config.app %>/**/*.html',
            '<%= config.app %>/styles/**/*.css',
            '<%= config.app %>/images/**/*',
            '<%= config.app %>/scripts/**/*.js'
          ],
          port: 8000,
          host: 'localhost',
          hostname: '192.168.1.146',
          server: {
            baseDir: ['<%= config.app %>/'],
            routes: {
              '/bower_components': './bower_components'
            }
          }
        }
      },
      dist: {
        options: {
          background: false,
          server: '<%= config.dist %>'
        }
      }
    },

    // Empties folders to start fresh
    clean: {
      dist: {
        files: [{
          dot: true,
          src: [
            '.tmp',
            '<%= config.dist %>/*',
            '!<%= config.dist %>/.git*'
          ]
        }]
      },
      server: ['.tmp','dist','dest']
    },

<<<<<<< HEAD
    //   Compiles Sass to CSS and generates necessary files if requested
=======
  //   Compiles Sass to CSS and generates necessary files if requested
>>>>>>> feature/webpack
    sass: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= config.app %>/sass/pages/server/',
<<<<<<< HEAD
          src: ['*.{scss,sass}'],
=======
          src: ['serverList.{scss,sass}'],
          //cwd: '<%= config.app %>/sass/pages/activityList/',
          //src: ['activityList.{scss,sass}'],
>>>>>>> feature/webpack
          dest: '.tmp/',
          ext: '.css'
        }]
      },
      server: {
        files: [{
          expand: true,
          cwd: '<%= config.app %>/sass/pages/server/',
<<<<<<< HEAD
=======
          //cwd: '<%= config.app %>/sass/pages/activityList/',
>>>>>>> feature/webpack
          src: ['*.{scss,sass}'],
          dest: '.tmp/',
          ext: '.css'
        }]
      }
    },

    autoprefixer: {
      options: {
        map: true,
        browsers: ["last 3 version","Android 4"]
      },
      dist: {
        files: [{
          expand: true,
          cwd: '.tmp/',
          src: 'serverList.css',
          //src: 'activityList.css',
          dest: 'dest/'
        }]
      },
      server: {
        files: [{
          expand: true,
          cwd: '.tmp/',
          src: '**/*.css',
          dest: 'dest/'
        }]
      },
      server: {
        files: [{
          expand: true,
          cwd: '.tmp/',
          src: '**/*.css',
          dest: '<%= config.app %>/styles/pages/server/'
        }]
      }
    },

    // Automatically inject Bower components into the HTML file
    wiredep: {
      app: {
        src: ['<%= config.app %>/pages/index.html'],
        exclude: ['bootstrap.js'],
        ignorePath: /^(\.\.\/)*\.\./
      },
      sass: {
        src: ['<%= config.app %>/sass/pages/activityList/*.{scss,sass}'],
        ignorePath: /^(\.\.\/)+/
      }
    },

    // Renames files for browser caching purposes
    filerev: {
      dist: {
        src: [
          '<%= config.dist %>/scripts/**/*.js',
          '<%= config.dist %>/sass/**/*.css',
          '<%= config.dist %>/images/**/*.*',
          '<%= config.dist %>/scss/fonts/**/*.*',
          '<%= config.dist %>/*.{ico,png}'
        ]
      }
    },

    // Reads HTML for usemin blocks to enable smart builds that automatically
    // concat, minify and revision files. Creates configurations in memory so
    // additional tasks can operate on them
    useminPrepare: {
      options: {
<<<<<<< HEAD
        dest: 'dist/pages/server'   //最终需修改引用路径的html文件所在的目录,预先通过 copy:dist 把html复制到此目录下
      },
      html: '<%= config.app %>/pages/server/**/*.html'  //原始html路径 文件引用部分使用 <!--build:{type} <path> --> <!--end build-->来创建block
=======
        dest: 'dist/pages/server'
        //dest: 'dist/pages/activityList'   //最终需修改引用路径的html文件所在的目录,预先通过 copy:dist 把html复制到此目录下
      },
      html: '<%= config.app %>/pages/server/serverList.html'
      //html: '<%= config.app %>/pages/activityList/activityList.html'  //原始html路径 文件引用部分使用 <!--build:{type} <path> --> <!--end build-->来创建block
>>>>>>> feature/webpack
    },

    // Performs rewrites based on rev and the useminPrepare configuration
    usemin: {
      options: {
        assetsDirs: [  //检测此目录下的被引用文件是否被修改
          'dist/pages',
          'dist/images',
          'dist/styles'
        ]
      },
<<<<<<< HEAD
      html:['dist/pages/server/**/*.html']  // 需修改引用路径的html文件
    },
=======
      html:['dist/pages/server/serverList.html']  // 需修改引用路径的html文件
      //html:['dist/pages/activityList/activityList.html']  // 需修改引用路径的html文件
},
>>>>>>> feature/webpack

    // The following *-min tasks produce minified files in the dist folder
    imagemin: {
      /* 压缩优化图片大小 */
      dist: {
        options: {
          optimizationLevel: 3
        },
        files: [
          {
            expand: true,
<<<<<<< HEAD
            cwd: '<%= config.app %>/images/pages/server/',
            src: '**/*', // 优化 img 目录下所有 png/jpg/jpeg/gif 图片
            dest: 'dist/images/pages/server/' // 优化后的图片保存位置，默认覆盖
=======
            cwd: '<%= config.app %>/images/pages/activityList/',
            src: '**/*', // 优化 img 目录下所有 png/jpg/jpeg/gif 图片
            dest: 'dist/images/pages/activityList/' // 优化后的图片保存位置，默认覆盖
>>>>>>> feature/webpack
          }
        ]
      }
    },

    // By default, your `index.html`'s <!-- Usemin block --> will take care
    // of minification. These next options are pre-configured if you do not
    // wish to use the Usemin blocks.
    cssmin: {
      /*压缩 CSS 文件为 .min.css */
      options: {
        keepSpecialComments: 0 /* 移除 CSS 文件中的所有注释 */
      },
      minify: {
        expand: true,
        cwd: 'dest/',
<<<<<<< HEAD
        src: '**/*.css',
        dest: 'dist/styles/pages/server/',
=======
        src: 'serverList.css',
        dest: 'dist/styles/pages/server/',
        //src: 'activityList.css',
        //dest: 'dist/styles/pages/activityList/',
>>>>>>> feature/webpack
        ext: '.min.css'
      }
    },

    uglify: {
      "my_target": {
<<<<<<< HEAD
        "files": [{
          'dist/scripts/server/app_formAll.js': ['<%= config.app %>/scripts/components/jquery.js',
            '<%= config.app %>/scripts/components/jquery.cookie.js',
            '<%= config.app %>/scripts/server/validation.js',
            '<%= config.app %>/scripts/server/formAll.js']
        },{
          'dist/scripts/server/app_servers.js': ['<%= config.app %>/scripts/components/jquery.js',
            '<%= config.app %>/scripts/components/jquery.cookie.js',
            '<%= config.app %>/scripts/server/servers.js']
        },{
          'dist/scripts/server/app_form.js': ['<%= config.app %>/scripts/components/jquery.js',
            '<%= config.app %>/scripts/components/jquery.cookie.js',
            '<%= config.app %>/scripts/server/validation.js',
            '<%= config.app %>/scripts/server/form.js']
        },{
          'dist/scripts/server/app_introduction.js': ['<%= config.app %>/scripts/components/jquery.js',
            '<%= config.app %>/scripts/components/jquery.cookie.js',
            '<%= config.app %>/scripts/server/introduction.js']
=======
        "files": [
        //  {
        //  'dist/scripts/server/app_formAll.js': ['<%= config.app %>/scripts/server/common.js',
        //    '<%= config.app %>/scripts/server/formAll_wp.js']
        //},{
        //  'dist/scripts/server/app_servers.js': ['<%= config.app %>/scripts/server/common.js',
        //    '<%= config.app %>/scripts/server/servers_wp.js']
        //},{
        //  'dist/scripts/server/app_form.js': ['<%= config.app %>/scripts/server/common.js',
        //    '<%= config.app %>/scripts/server/form_wp.js']
        //},
          {
            'dist/scripts/server/app_serverList.js': ['<%= config.app %>/scripts/server/serverList_wp.js']
            //'dist/scripts/activityList/app_activityList.js': ['<%= config.app %>/scripts/activityList/activityList_wp.js']
>>>>>>> feature/webpack
        }]
      }
    },
    //concat: {
    //  /* 合并 CSS 文件 */
    //  css: {
    //    src: '<%= config.app %>/styles/pages/server/**/*.css',
    //    /* 根据目录下文件情况配置 */
    //    dest: 'dest/css/all.css'
    //  }
    //},
    // Copies remaining files to places other tasks can use
    copy: {
      dist: {
        files: [{
          expand: true,
          dot: true,
          cwd: '<%= config.app %>/',
<<<<<<< HEAD
          src: 'pages/server{,/*}',
=======
          src: 'pages/server/serverList.html',
          //src: ['pages/activityList/activityList.html'],
>>>>>>> feature/webpack
          dest: 'dist/'
        }]
      },
      default: {
        files: [{
          expand: true,
          dot: true,
          cwd: '<%= config.app %>/',
<<<<<<< HEAD
          src: ['sass/pages/server{,/*}','scripts/server/js_ForDev/**/*'],
          dest: 'D:\\yiwork_0918\\yiwork_0918\\WebRoot\\'
=======
          src: ['sass/pages/activityList{,/*}','scripts/activityList/js_ForDev/**/*'],
          dest: 'D:\\yiwork_0918\\yiwork_20150708\\WebRoot\\'
>>>>>>> feature/webpack
        }]
      },
      server: {
        files: [{
          expand: true,
          dot: true,
          cwd: 'dist/',
          src: ['**'],
          dest: 'D:\\yiwork_0918\\yiwork_0918\\WebRoot\\'
        }]
      }
    },

    // Run some tasks in parallel to speed up build process
    concurrent: {
      server: [
        'babel:dist',
        'sass:server'
      ],
      preComplete: [
        'webpack',
        'sass:dist'
      ],
      dist: [
        'uglify',
        'imagemin',
        'cssmin'
      ]
    },
    webpack: {
      build: {
        entry:{
          //formAll_wp:'D:\\mywork\\yiwork_20150708\\WebRoot\\scripts\\server\\js_ForDev\\formAll.js',
          //form_wp:'D:\\mywork\\yiwork_20150708\\WebRoot\\scripts\\server\\js_ForDev\\form.js',
          //servers_wp:'D:\\mywork\\yiwork_20150708\\WebRoot\\scripts\\server\\js_ForDev\\servers.js',
          serverList_wp:'D:\\mywork\\yiwork_20150708\\WebRoot\\scripts\\server\\js_ForDev\\serverList.js',
          //activityList_wp:'D:\\mywork\\yiwork_20150708\\WebRoot\\scripts\\activityList\\js_ForDev\\activityList.js'
        },
        output: {
          path: "<%= config.app %>/scripts/server/",
          //path: "<%= config.app %>/scripts/activityList/",
          filename: "[name].js"
        },
        module: {
          loaders: [
            {
              test:/\.scss$/,
              loader: 'style!css!autoprefixer?{browsers:["last 3 version","Android 4"]}!sass'
            }
             //{ test: /\.handlebars$/, loader: "handlebars-loader" }
          ]
        },
        externals: {
          // require('data') is external and available
          //  on the global var data
          //jquery: 'jQuery',
          'isDev': 'false',
        },
        node: {
          fs: "empty"
        },
        plugins:[
          new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery"
          }),
           //new commonPlugins('common.js')
        ]
      }
    }
  });


  grunt.registerTask('serve', 'start the server and preview your app', function (target) {

    if (target === 'dist') {
      return grunt.task.run(['build', 'browserSync:dist']);
    }

    grunt.task.run([
      'clean:server',
      'sass:server',
      'autoprefixer:server',
<<<<<<< HEAD
=======
      'webpack',
>>>>>>> feature/webpack
      'browserSync:livereload',
      'watch'
    ]);
  });

  grunt.registerTask('server', function (target) {
    grunt.log.warn('The `server` task has been deprecated. Use `grunt serve` to start a server.');
    grunt.task.run([target ? ('serve:' + target) : 'serve']);
  });

  grunt.registerTask('build', [
    'clean',  //清除临时文件夹
    'copy:dist',   //复制html文件供usemin使用
    'useminPrepare',
<<<<<<< HEAD
    'sass:dist',
=======
    'concurrent:preComplete',  //并行的 webpack sass:dist
>>>>>>> feature/webpack
    'autoprefixer:dist',
    'concurrent:dist',  //并行的 cssmin uglify imagemin
    'usemin',
    'copy:server',  //把处理好的在 dist/ 下的文件复制到工作目录中
<<<<<<< HEAD
    'copy:default'  //把原始的 scss js 文件复制到工作目录中
=======
    //'copy:default'  //把原始的 scss js 文件复制到工作目录中
>>>>>>> feature/webpack
  ]);

  grunt.registerTask('test', ['clean']);

  grunt.registerTask('default', [
    'newer:eslint',
    'build'
  ]);

  grunt.loadNpmTasks('grunt-webpack');

};
