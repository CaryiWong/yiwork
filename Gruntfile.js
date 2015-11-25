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
      babel: {
        files: ['<%= config.app %>/scripts/**/*.js']
      },
      gruntfile: {
        files: ['Gruntfile.js']
      },
      sass: {
        files: ['<%= config.app %>/sass/pages/server/*.{scss,sass}'],
        tasks: ['newer:clean:server','newer:sass:server','newer:autoprefixer']
      }
    },
    babel: {
      options: {
        sourceMap: true
      },
      dist: {
        files: {
          '/scripts/server/**/*.js':'<%= config.app %>/scripts/server/**/*.js'
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

    //   Compiles Sass to CSS and generates necessary files if requested
    sass: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= config.app %>/sass/pages/server/',
          src: ['*.{scss,sass}'],
          dest: '.tmp/',
          ext: '.css'
        }]
      },
      server: {
        files: [{
          expand: true,
          cwd: '<%= config.app %>/sass/pages/server/',
          src: ['*.{scss,sass}'],
          dest: '.tmp/',
          ext: '.css'
        }]
      }
    },

    autoprefixer: {
      options: {
        map: true,
        browsers: ['> 1%', 'last 2 versions', 'Firefox ESR', 'Opera 12.1']
      },
      dist: {
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
        src: ['<%= config.app %>/sass/pages/server/*.{scss,sass}'],
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
        dest: 'dist/pages/server'   //最终需修改引用路径的html文件所在的目录,预先通过 copy:dist 把html复制到此目录下
      },
      html: '<%= config.app %>/pages/server/**/*.html'  //原始html路径 文件引用部分使用 <!--build:{type} <path> --> <!--end build-->来创建block
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
      html:['dist/pages/server/**/*.html']  // 需修改引用路径的html文件
    },

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
            cwd: '<%= config.app %>/images/pages/server/',
            src: '**/*', // 优化 img 目录下所有 png/jpg/jpeg/gif 图片
            dest: 'dist/images/pages/server/' // 优化后的图片保存位置，默认覆盖
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
        src: '**/*.css',
        dest: 'dist/styles/pages/server/',
        ext: '.min.css'
      }
    },

    uglify: {
      "my_target": {
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
          src: 'pages/server{,/*}',
          dest: 'dist/'
        }]
      },
      default: {
        files: [{
          expand: true,
          dot: true,
          cwd: '<%= config.app %>/',
          src: ['sass/pages/server{,/*}','scripts/server/js_ForDev/**/*'],
          dest: 'D:\\yiwork_0918\\yiwork_0918\\WebRoot\\'
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
      dist: [
        'uglify',
        'imagemin',
        'cssmin'
      ]
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
    'sass:dist',
    'autoprefixer:dist',
    'concurrent:dist',  //并行的 cssmin uglify imagemin
    'usemin',
    'copy:server',  //把处理好的在 dist/ 下的文件复制到工作目录中
    'copy:default'  //把原始的 scss js 文件复制到工作目录中
  ]);

  grunt.registerTask('test', ['clean']);

  grunt.registerTask('default', [
    'newer:eslint',
    'build'
  ]);

  grunt.loadNpmTasks('grunt-webpack');

};
