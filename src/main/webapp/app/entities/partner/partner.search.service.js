(function() {
    'use strict';

    angular
        .module('hubApp')
        .factory('PartnerSearch', PartnerSearch);

    PartnerSearch.$inject = ['$resource'];

    function PartnerSearch($resource) {
        var resourceUrl =  'api/_search/partners/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
