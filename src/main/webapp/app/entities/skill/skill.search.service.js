(function() {
    'use strict';

    angular
        .module('hubApp')
        .factory('SkillSearch', SkillSearch);

    SkillSearch.$inject = ['$resource'];

    function SkillSearch($resource) {
        var resourceUrl =  'api/_search/skills/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
