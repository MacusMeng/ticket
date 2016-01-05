'use strict';

angular.module('userUi').constant('EnumExceptionMappings', [
  {
    id: 400,
    label: 'bad_request'
  },
  {
    id: 404,
    label: 'not_exist'
  },
  {
    id: 409,
    label: 'conflict'
  },
  {
    id: 422,
    label: 'Invalid parameters'
  }
]);
