
angular.module('app').factory('UserService', ['$http', '$q', function ($http, $q) {
        var factory = {
            Create: Create
        };

        return factory;


        function Create(user) {
            var deferred = $q.defer();
            $http.post('resource/user/create', user)
                    .then(
                            function (response) {
                                deferred.resolve(response.data);
                            },
                            function (errResponse) {
                                console.error('Registration faild');
                                deferred.reject(errResponse);
                            }
                    );
            return deferred.promise;
        }
    }]);
