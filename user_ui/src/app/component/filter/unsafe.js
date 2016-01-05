'use strict';

angular.module('userUi').filter('unsafe', function ($sce) {
  return function (val) {
    return $sce.trustAsHtml(val);
  };
});
