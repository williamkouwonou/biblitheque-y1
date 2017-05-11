'use strict';
var myapp;
myapp.service('Session', function () {
    this.create = function (data) {
        this.id = data.id;
        this.login = data.login;
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.email = data.email;
        this.userRoles = [];
        angular.forEach(data.authorities, function (value, key) {
            this.push(value);
        }, this.userRoles);
    };
    this.invalidate = function () {
        this.id = null;
        this.login = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.userRoles = null;
    };
    return this;
});


myapp.service('AuthSharedService', function ($rootScope, $http, authService, Session) {
    return {
        getAccount: function () {
            $rootScope.loadingAccount = true;
            $http.get('userinfo')
                    .then(function (response) {
                        if (response.data.name) {
                            authService.loginConfirmed(response.data);
                            $rootScope.userinfo.dateNaissance = new Date($rootScope.userinfo.dateNaissance);
                            $rootScope.name = response.data.name;
                            $rootScope.authenticated = true;
                        } else {
                            $rootScope.authenticated = false;
                        }

                    }, function () {
                        $rootScope.authenticated = false;
                    });
        },
        isAuthorized: function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles === '*') {
                    return true;
                }
                authorizedRoles = [authorizedRoles];
            }
            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!Session.login &&
                        Session.userRoles.indexOf(authorizedRole) !== -1);
                if (authorized || authorizedRole === '*') {
                    isAuthorized = true;
                }
            });
            return isAuthorized;
        },
        logout: function () {
            var req = {
                method: 'POST',
                url: "logout",
                xhrFields: {withCredentials: true}

            };
            $rootScope.authenticated = false;

            $rootScope.account = null;
            $http(req);
            Session.invalidate();
            authService.loginCancelled();
        }
    };
});

