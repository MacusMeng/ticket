'use strict';

angular.module('adminUi').constant('EnumTicketStatus', [
  {
    id: 'CREATED',
    label: 'enum_ticket_status_created'
  },
  {
    id: 'REPLIED',
    label: 'enum_ticket_status_replied'
  },
  {
    id: 'USER_REPLY',
    label: 'enum_ticket_status_user_reply'
  },
  {
    id: 'CLOSED',
    label: 'enum_ticket_status_closed'
  }
]);
