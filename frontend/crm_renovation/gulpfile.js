// include GULP package
var gulp = require('gulp');

// include plugin
var jshint = require('gulp-jshint');
var sass = require('gulp-sass');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');
var browserSync = require('browser-sync').create();
var clean = require('gulp-clean');
var plumber = require('gulp-plumber');
var imagemin = require('gulp-imagemin');
var sourcemaps = require('gulp-sourcemaps');
var changed = require('gulp-changed');
var ngAnnotate = require('gulp-ng-annotate');



// clean The Dist Directory
gulp.task('clean-files', function() {
    return gulp.src('./dist/*', { read: false })
        .pipe(clean());
});

// Changed in directory
var Lib_SRC = './src/lib/*';
var Lib_DEST = './dist/lib';
gulp.task('changedLib', function() {
    return gulp.src(Lib_SRC)
        .pipe(changed(Lib_DEST))
        .pipe(ngAnnotate())
        .pipe(gulp.dest(Lib_DEST));
});

// jshint
gulp.task('jshint', function() {
    return gulp.src('./src/js/*.js')
        .pipe(plumber())
        .pipe(jshint())
        .pipe(jshint.reporter('default'))
        .pipe(browserSync.stream());
});

// SCSS Compiler
gulp.task('sass', function() {
    return gulp.src('./src/scss/*scss')
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(sourcemaps.write())
        .pipe(gulp.dest('./dist/css'))
        .pipe(browserSync.stream());
});

// minify JS
gulp.task('uglify-js', function() {
    return gulp.src('./src/js/*js')
        .pipe(uglify())
        .pipe(gulp.dest('./dist/js'))
        .pipe(browserSync.stream());
});

// Copy liberary file
gulp.task('copy-files', function() {
    return gulp.src('./src/lib/*').pipe(gulp.dest('./dist/lib/*'));
});

// Copy WebFont Folder
gulp.task('copy-WebFonts', function() {
    return gulp.src('./src/webfonts/*').pipe(gulp.dest('./dist/webfonts'));
});


// Make bundle js file using concat uglify plugin

var jQueryPath = "node_modules/jquery/dist/jquery.js";
var popperJsPath = "node_modules/popper.js/dist/umd/popper.min.js";
var bootstrapJsPath = "node_modules/bootstrap/dist/js/bootstrap.js";
var fluxMenuPath = "./src/js/fluxMenu.js";

gulp.task('bundle', function() {
    return gulp.src([jQueryPath, popperJsPath, bootstrapJsPath, fluxMenuPath, './src/js/*js'])
        .pipe(plumber())
        .pipe(concat('bundle.js'))
        .pipe(uglify())
        .pipe(gulp.dest('./dist/js'))
        .pipe(browserSync.stream());
});

/*gulp.task('bundle', function() {
    return gulp.src([jQueryPatch, bootstrapJsPath, './src/js/*js'])
        .pipe(plumber())
        .pipe(concat('bundle.js'))
        .pipe(uglify())
        .pipe(gulp.dest('./dist/js'))
        .pipe(browserSync.stream());
});*/

// sync browser with the Project
gulp.task('browser-sync', function() {
    browserSync.init({
        watch: true,
        server: {
            baseDir: "./"
        }
    });
    gulp.watch('./src/scss/*scss', gulp.series('sass'));
    gulp.watch('./src/js/*js', gulp.series('jshint'));
    gulp.watch('./src/js/*js', gulp.series('bundle'));
    gulp.watch('./*html').on('change', browserSync.reload);
});

// Watch Function
gulp.watch('./src/lib/*', gulp.series('changedLib'));
// Default function
gulp.task('default', gulp.series(
    'clean-files',
    'sass',
    'jshint',
    'copy-WebFonts',
    'bundle',
    'changedLib',
    'browser-sync'

));