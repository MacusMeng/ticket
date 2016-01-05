'use strict';

angular.module('adminUi').factory('Login', function LoginFactory($resource) {
  return $resource('/ticket-admin/api/admins/login');
});

angular.module('adminUi').factory('Register', function RegisterFactory($resource) {
  return $resource('/ticket-admin/api/admins/register');
});

angular.module('adminUi').factory('Logout', function LogoutFactory($resource) {
  return $resource('/ticket-admin/api/admins/logout');
});
