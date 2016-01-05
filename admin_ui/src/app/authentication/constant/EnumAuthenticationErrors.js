'use strict';

angular.module('adminUi').constant('EnumAuthenticationErrors', [
  {
    id: 'INVALID_USERNAME_OR_PASSWORD',
    label: 'authentication_message_invalid_username_or_password'
  },
  {
    id: 'INVALID_CAPTCHA',
    label: 'authentication_message_invalid_captcha'
  },
  {
    id: 'INVALID_PASSWORD',
    label: 'authentication_message_invalid_password'
  },
  {
    id: 'INVALID_USERNAME',
    label: 'authentication_message_invalid_username'
  },
  {
    id: 'USERNAME_OR_EMAIL_LARGE_THAN_100',
    label: 'authentication_message_username_or_email_maxlength'
  },
  {
    id: 'PASSWORD_LARGE_THAN_100',
    label: 'authentication_message_password_maxlength'
  },
  {
    id: 'USERNAME_OR_EMAIL_REQUIRED',
    label: 'authentication_message_username_or_email_required'
  },
  {
    id: 'PASSWORD_REQUIRED',
    label: 'authentication_message_password_required'
  },
  {
    id: 'INVALID_EMAIL_ADDRESS',
    label: 'authentication_message_invalid_email'
  },
  {
    id: 'EMAIL_REQUIRED',
    label: 'authentication_message_email_required'
  },
  {
    id: 'MODULES_REQUIRED',
    label: 'authentication_message_modules_required'
  },
  {
    id: 'DUPLICATE_USERNAME_OR_EMAIL',
    label: 'authentication_message_duplicate_username_or_email'
  }
]);
