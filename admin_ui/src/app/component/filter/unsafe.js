'use strict';

angular.module('adminUi').filter('unsafe', function ($sce) {
  return function (val) {
    return $sce.trustAsHtml(val);
  };
});
