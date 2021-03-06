'use strict';

/* Services */

/*
 http://docs.angularjs.org/api/ngResource.$resource

 Default ngResources are defined as

 'get':    {method:'GET'},
 'save':   {method:'POST'},
 'query':  {method:'GET', isArray:true},
 'remove': {method:'DELETE'},
 'delete': {method:'DELETE'}

 */

var userServices = angular.module('userServices', ['ngResource']);


userServices.factory('UsersFactory', function ($resource) {
    return $resource('users', {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST' }
    })
});

userServices.factory('UserFactory', function ($resource) {
    return $resource('user/:id', {}, {
        show: { method: 'GET' },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});
