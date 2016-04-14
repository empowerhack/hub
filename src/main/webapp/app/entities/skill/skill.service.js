(function() {
    'use strict';
    angular
        .module('hubApp')
        .factory('Skill', Skill);

    Skill.$inject = ['$resource'];

    function Skill ($resource) {
        var resourceUrl =  'api/skills/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
