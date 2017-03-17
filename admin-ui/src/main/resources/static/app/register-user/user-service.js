
angular.module('app').factory('UserService', ['$http', '$q', function ($http, $q) {
        var factory = {
            Create: Create,
            ListbyPage:ListByPage,
            List:List
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
        function ListByPage(size,page,mot) {
            var deferred = $q.defer();
            $http.get('resource/user/listpage?page='+page+'&size='+size+'&mot='+mot)
                    .then(
                            function (response) {
                                deferred.resolve(response.data);
                            },
                            function (errResponse) {
                                console.error(' faild');
                                deferred.reject(errResponse);
                            }
                    );
            return deferred.promise;
        }
   
        function List(mot) {
            var deferred = $q.defer();
            $http.get('resource/user/list?mot='+mot)
                    .then(
                            function (response) {
                                deferred.resolve(response.data);
                            },
                            function (errResponse) {
                                console.error(' faild');
                                deferred.reject(errResponse);
                            }
                    );
            return deferred.promise;
        }
   
    
    }]);
