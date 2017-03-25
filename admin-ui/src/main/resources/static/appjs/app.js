angular.module('app', ['ngRoute', 'ngFlash', 'ngCookies']).config(function ($routeProvider, $httpProvider) {


    $routeProvider
            .when('/', {
                templateUrl: 'home.html',
                controller: 'home',
                controllerAs: 'vm'
            })

            .when('/register', {
                templateUrl: 'app/register-user/register.html',
                controller: 'UserController',
                controllerAs: 'vm'
            })
            .when('/list', {
                templateUrl: 'app/register-user/list.html',
                controller: 'UserController',
                controllerAs: 'vm'
            })
            .when('/profile', {
                templateUrl: 'app/register-user/profile.html',
                controller: 'UserController',
                controllerAs: 'vm'
            })
            .otherwise('login');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    $httpProvider.defaults.headers.common['Accept'] = 'application/json';

})
        .controller('navigation',
                function ($rootScope, $http, $location, $route, $cookies, $cookieStore) {

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
                    $http.get('userinfo').then(function (response) {

                        if (response.data.name) {
                            $rootScope.userinfo=response.data;
                            
                            $rootScope.userinfo.dateNaissance=new Date($rootScope.userinfo.dateNaissance);
                            $rootScope.name = response.data.name;

                        }
                    });

                    self.credentials = {};

                    self.voirProfile = function () {
                         $location.path("/profile");
                    };
                    self.logout = function () {
                        var req = {
                            method: 'POST',
                            url: "logout",
                            xhrFields: {withCredentials: true}

                        };
                        var req2 = {
                            method: 'GET',
                            url: "globale/logout",
                            xhrFields: {withCredentials: true}

                        };
                         
                        $http(req2).finally(function () {
                           
                        });
                        $http(req).finally(function () {
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
