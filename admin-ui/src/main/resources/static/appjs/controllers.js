'use strict';

var app;
app.controller('navigation',
        function ($rootScope, $http, $location, $route, $cookies, $cookieStore, AuthSharedService) {

            var self = this;

            self.tab = function (route) {
                return $route.current && route === $route.current.controller;
            };

           AuthSharedService.getAccount();
            
            self.credentials = {};

            self.voirProfile = function () {
                $location.path("/profile");
            };
            self.logout = function () {
                AuthSharedService.logout();
                $location.path("/");

            };

        })


//        .controller('RegisterController', function ($scope, RegisterService) {
//        })


        .controller('home', function ($http) {
            var self = this;
            $http.get('resources/').then(function (response) {
                console.info(JSON.stringify(response.data));
                self.greeting = response.data;
            });

        })
        .controller('HomeController', function ($scope) {


        })


        .controller('LogoutController', function (AuthSharedService) {
            AuthSharedService.logout();
        })

        .controller('ErrorController', function ($scope, $routeParams) {
            $scope.code = $routeParams.code;

            switch ($scope.code) {
                case "403" :
                    $scope.message = "Accès non autorisé.";
                    break;
                case "404" :
                    $scope.message = "Page not trouvée.";
                    break;
                default:
                    $scope.code = 500;
                    $scope.message = "Oops! Erreur interne au serveur";
            }
        });
