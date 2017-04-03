
angular.module('app').factory('EmpruntService', ['$http', '$q', function ($http, $q) {
        var factory = {
            Create: Create,
            Update: Update,
            ListbyPage:ListByPage,
            List:List

           
        };

        return factory;


        function Create(isbn,login) {
            var deferred = $q.defer();
            $http.get('resource/emprunt/create/'+isbn+'/'+login)
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
        function Update(id) {
            var deferred = $q.defer();
            $http.post('resource/emprunt/update',id)
                    .then(
                            function (response) {
                                deferred.resolve(response.data);
                            },
                            function (errResponse) {
                                console.error('update faild');
                                deferred.reject(errResponse);
                            }
                    );
            return deferred.promise;
        }
        function ListByPage(parameters) {
            var deferred = $q.defer();
            $http.post('resource/emprunt/searchepage/',parameters)
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
   
        function List(parameters) {
            var deferred = $q.defer();
            $http.post('resource/emprunt/searche',parameters)
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
