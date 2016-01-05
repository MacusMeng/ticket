"use strict";

angular.module('adminUi').config(function (uiSelectConfig) {
  uiSelectConfig.theme = 'bootstrap';
});

angular.module('adminUi').config(function ($httpProvider) {
  $httpProvider.interceptors.push(function ($translate) {
    return {
      request: function (config) {
        if (!_.includes(config.url, '/api/')) {
          return config;
        }

        if (!config.params) {
          config.params = {};
        }
        config.params.locale = $translate.proposedLanguage() || $translate.use();

        return config;
      }
    }
  });
});


angular.module('adminUi').run(function ($rootScope, $state, $location, authentication,
                                        navData, paginationConfig, $filter, $translate, NoAuthStates, layout) {
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

    if (!_.some(NoAuthStates, function (item) {
        return next.indexOf(item.url) >= 0;
      })) {
      layout.setShowNav(true);
      jQuery.ajax({
        url: '/ticket-admin/api/admins/current-admin',
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        async: false,
        statusCode: {
          401: function () {
            $location.path('/login');
          }
        },
        data: {locale: $translate.use()},
        success: function (user) {
          authentication.setCurrentUser(user);
        }
      });
    } else {
      layout.setShowNav(false);
    }
  });
});
