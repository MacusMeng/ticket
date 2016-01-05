'use strict';

angular.module('userUi').constant('EnumTicketErrors', [
  {
    id: 'TICKET_TITLE_REQUIRED',
    label: 'ticket_add_message_title_not_null'
  },
  {
    id: 'TICKET_TITLE_LARGE_THAN_255',
    label: 'ticket_add_message_title_max_length'
  },
  {
    id: 'TICKET_MODULE_REQUIRED',
    label: 'ticket_add_message_module_not_null'
  },
  {
    id: 'TICKET_CONTENT_REQUIRED',
    label: 'ticket_add_message_content_not_null'
  },
  {
    id: 'FILE_ALREADY_EXIST',
    label: 'ticket_add_message_file_exist'
  },
  {
    id: 'FILE_UPLOAD_IO_EXCEPTION',
    label: 'ticket_add_message_file_upload_error'
  },
  {
    id: 'FILE_DELETE_IO_EXCEPTION',
    label: 'ticket_add_message_file_delete_error'
  },
  {
    id: 'REPLY_CONTENT_REQUIRED',
    label: 'reply_add_message_content_not_null'
  },
  {
    id: 'TICKET_INPUT_ERROR',
    label: 'input_error_ticket_id'
  }
]);
