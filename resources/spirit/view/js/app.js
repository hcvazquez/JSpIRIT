'use strict';

// Declare app level module which depends on filters, and services
var app = angular.module('spirit', 
		[ "ngRoute",
		  "googlechart",
		  'spirit.services',
		  'spirit.controllers',
		  'angularMoment',
		  'ui.bootstrap',
		  'spirit.directives'
		])
	
