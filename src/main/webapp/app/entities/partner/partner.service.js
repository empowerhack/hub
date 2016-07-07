(function() {
    'use strict';
    angular
        .module('hubApp')
        .factory('Partner', Partner);

    Partner.$inject = ['$resource'];

    function Partner ($resource) {
        var resourceUrl =  'api/partners/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
