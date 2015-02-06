'use strict';

module.exports = function(grunt) {

  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-connect-proxy');

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    watch: {

      css: {
        files: 'scss/*.scss',
        tasks: ['sass'],
        options: {
          livereload: true
        }
      },

      livereload: {
        options: {
          livereload: '<%= connect.dev.options.livereload %>'
        },
        files: [
          'www/**/*.{html,js}',
          'www/img/*.{png,jpg,jpeg,gif}',
          'gruntfile.js',
        ]
      }

    },

    sass: {

      main: {
        files: {
          'www/css/ionic.css': 'scss/ionic.app.scss',
          'www/css/main.css': 'scss/main.scss'
        }
      }
    },

    connect: {

      options: {
        open: true,
        hostname: 'localhost',
        middleware: function (connect,options) {
          var proxy = require('grunt-connect-proxy/lib/utils').proxyRequest;
          var base = options.base[0];
          return [
            proxy,
            connect.static(base)
          ];
        }
      },
      dev: {
        options: {
          base:'www',
          port: 8100,
          livereload: 35729
        }
      },
      proxies: [
        {
          context: '/rest',
          host: 'localhost',
          port: 8080
        }
      ]
    },


    jshint: {
      options: {
        jshintrc: '.jshintrc',
        reporter: require('jshint-stylish')
      },
      all: [
        'Gruntfile.js',
        'www/js/**/*.js'
      ]
    }


  });


  grunt.registerTask('dev', [
    'jshint',
    'sass',
    'configureProxies',
    'connect:dev',
    'watch'
  ]);


};
