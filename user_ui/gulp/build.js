'use strict';

var gulp = require('gulp');

var $ = require('gulp-load-plugins')({
  pattern: ['gulp-*', 'main-bower-files', 'uglify-save-license', 'del']
});

module.exports = function (options) {
  gulp.task('partials', function () {
    return gulp.src([
      options.src + '/app/**/*.html',
      options.tmp + '/serve/app/**/*.html'
    ])
      .pipe($.minifyHtml({
        empty: true,
        spare: true,
        quotes: true
      }))
      .pipe($.angularTemplatecache('templateCacheHtml.js', {
        module: 'userUi',
        root: 'app'
      }))
      .pipe(gulp.dest(options.tmp + '/partials/'));
  });

  gulp.task('html', ['inject', 'partials'], function () {
    var partialsInjectFile = gulp.src(options.tmp + '/partials/templateCacheHtml.js', {read: false});
    var partialsInjectOptions = {
      starttag: '<!-- inject:partials -->',
      ignorePath: options.tmp + '/partials',
      addRootSlash: false
    };

    var htmlFilter = $.filter('*.html');
    var jsFilter = $.filter('**/*.js');
    var cssFilter = $.filter('**/*.css');
    var assets;

    return gulp.src(options.tmp + '/serve/*.html')
      .pipe($.inject(partialsInjectFile, partialsInjectOptions))
      .pipe(assets = $.useref.assets())
      .pipe($.rev())
      .pipe(jsFilter)
      .pipe($.ngAnnotate())
      .pipe($.uglify({preserveComments: $.uglifySaveLicense})).on('error', options.errorHandler('Uglify'))
      .pipe(jsFilter.restore())
      .pipe(cssFilter)
      .pipe($.replace('../../bower_components/bootstrap-sass-official/assets/fonts/bootstrap/', '../fonts/'))
      .pipe($.replace('../../bower_components/font-awesome/fonts/', '../fonts/'))
      .pipe($.replace('fonts/tinymce.eot', '../fonts/tinymce.eot'))
      .pipe($.replace('fonts/tinymce.svg', '../fonts/tinymce.svg'))
      .pipe($.replace('fonts/tinymce.ttf', '../fonts/tinymce.ttf'))
      .pipe($.replace('fonts/tinymce.woff', '../fonts/tinymce.woff'))
      .pipe($.replace('fonts/tinymce-small.eot', '../fonts/tinymce-small.eot'))
      .pipe($.replace('fonts/tinymce-small.svg', '../fonts/tinymce-small.svg'))
      .pipe($.replace('fonts/tinymce-small.ttf', '../fonts/tinymce-small.ttf'))
      .pipe($.replace('fonts/tinymce-small.woff', '../fonts/tinymce-small.woff'))
      .pipe($.replace('img/object.gif', '../scripts/plugins/emoticons/img/object.gif'))
      .pipe($.replace('img/anchor.gif', '../scripts/plugins/emoticons/img/anchor.gif'))
      .pipe($.replace('img/loader.gif', '../scripts/plugins/emoticons/img/loader.gif'))
      .pipe($.replace('img/trans.gif', '../scripts/plugins/emoticons/img/trans.gif'))
      .pipe($.csso())
      .pipe(cssFilter.restore())
      .pipe(assets.restore())
      .pipe($.useref())
      .pipe($.revReplace())
      .pipe(htmlFilter)
      .pipe($.minifyHtml({
        empty: true,
        spare: true,
        quotes: true,
        conditionals: true
      }))
      .pipe(htmlFilter.restore())
      .pipe(gulp.dest(options.dist + '/'))
      .pipe($.size({title: options.dist + '/', showFiles: true}));
  });

  // Only applies for fonts from bower dependencies
  // Custom fonts are handled by the "other" task
  gulp.task('fonts', function () {
    return gulp.src($.mainBowerFiles())
      .pipe($.filter('**/*.{eot,svg,ttf,woff,woff2}'))
      .pipe($.flatten())
      .pipe(gulp.dest(options.dist + '/fonts/'));
  });

  gulp.task('other', function () {
    return gulp.src([
      options.src + '/**/*',
      '!' + options.src + '/**/*.{html,css,js,scss}'
    ])
      .pipe(gulp.dest(options.dist + '/'));
  });

  gulp.task('tiny_emoticon_img', function () {
    return gulp.src($.mainBowerFiles())
      .pipe($.filter('**/*.gif'))
      .pipe(gulp.dest(options.dist + '/scripts/plugins/emoticons/img/'));
  });

  gulp.task('clean', function (done) {
    $.del([options.dist + '/', options.tmp + '/'], done);
  });

  gulp.task('build', ['html', 'fonts', 'other', 'tiny_emoticon_img']);
};
