'use strict';

angular.module('adminUi').constant('EnumTicketErrors', [
  {
    id: 'REPLY_CONTENT_REQUIRED',
    label: 'reply_add_message_content_not_null'
  },
  {
    id: 'TICKET_INPUT_ERROR',
    label: 'input_error_ticket_id'
  },
  {
    id:'TICKET_INPUT_CLOSED',
    label:'admin_reply_when_ticket_closed'
  }
]);
