"use strict";

angular.module('userUi').config(function (uiSelectConfig) {
  uiSelectConfig.theme = 'bootstrap';

});

angular.module('userUi').run(function ($rootScope, $state, $location, authentication,
                                       navData, paginationConfig, $filter, $translate) {
  var translate = $filter('translate');
  paginationConfig.firstText = translate('button_first');
  paginationConfig.lastText = translate('button_last');
  paginationConfig.previousText = translate('button_previous');
  paginationConfig.nextText = translate('button_next');

  $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams) {
    navData.current.state = toState;
    navData.current.params = toParams;
  });

  $rootScope.$on('$locationChangeStart', function (event, next) {
    authentication.setState(next);

    jQuery.ajax({
      url: '/ticket/api/users/current-user',
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      async: false,
//todo:Now should currentUser always return success.
      //statusCode: {
      //  401: function () {
      //    $location.path('/login');
      //  }
      //},
      success: function (user) {
        console.log(user);
        authentication.setCurrentUser(user);
        $translate.use(user.language);
      }
    });

  });
});
