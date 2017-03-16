angular.module('app', ['ngRoute','ngFlash']).config(function ($routeProvider, $httpProvider) {

  
    $routeProvider
            .when('/', {
                templateUrl: 'home.html',
                controller: 'home',
                controllerAs: 'vm'
            })

            .when('/register', {
                templateUrl: 'app/register-user/register.html',
                controller: 'RegisterUserController',
                controllerAs: 'vm'
            }).otherwise('/');

   $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    $httpProvider.defaults.headers.common['Accept'] = 'application/json';

})
        .controller('navigation',
        function ($rootScope, $http, $location, $route) {

            var self = this;
      
            self.tab = function (route) {
                return $route.current && route === $route.current.controller;
            };

            $http.get('user').then(function (response) {
               
                if (response.data.name) {

                    $rootScope.authenticated = true;
                    self.nom = response.data.name;
                } else {
                    $rootScope.authenticated = false;
                }
            }, function () {
                $rootScope.authenticated = false;
            });

            self.credentials = {};

            self.logout = function () {
                $http.post('logout', {}).finally(function () {
                    $rootScope.authenticated = false;
                    self.nom = '';
                    $location.path("/");
                });
            };

        }).controller('home', function ($http) {
    var self = this;
    $http.get('resources/').then(function (response) {
        console.info(JSON.stringify(response.data));
        self.greeting = response.data;
    });
});
