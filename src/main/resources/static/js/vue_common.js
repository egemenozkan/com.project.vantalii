import Vue from 'vue';

// This variable will hold the reference to
// document's click handler
var handleOutsideClick;
Vue.directive('closable', {
    bind: function bind(el, binding, vnode) {
        // Here's the click/touchstart handler
        // (it is registered below)
        handleOutsideClick = function handleOutsideClick(e) {
            e.stopPropagation(); // Get the handler method name and the exclude array
            // from the object used in v-closable

            var _binding$value = binding.value;
             var   handler = _binding$value.handler;
                var exclude = _binding$value.exclude; // This variable indicates if the clicked element is excluded

            var clickedOnExcludedEl = false;
            exclude.forEach(function (refName) {
                // We only run this code if we haven't detected
                // any excluded element yet
                if (!clickedOnExcludedEl) {
                    // Get the element using the reference name
                    var excludedEl = vnode.context.$refs[refName]; // See if this excluded element
                    // is the same element the user just clicked on

                    if (typeof excludedEl !== 'undefined' && excludedEl) {
                        clickedOnExcludedEl = excludedEl.contains(e.target);
                        console.log("excludeed", excludedEl);
                    }
                }
            }); // We check to see if the clicked element is not
            // the dialog element and not excluded

            if (!el.contains(e.target) && !clickedOnExcludedEl) {
                // If the clicked element is outside the dialog
                // and not the button, then call the outside-click handler
                // from the same component this directive is used in
                vnode.context[handler]();
            }
        }; // Register click/touchstart event listeners on the whole page


        document.addEventListener('click', handleOutsideClick);
        document.addEventListener('touchstart', handleOutsideClick);
    },
    unbind: function unbind() {
        // If the element that has v-closable is removed, then
        // unbind click/touchstart listeners from the whole page
        document.removeEventListener('click', handleOutsideClick);
        document.removeEventListener('touchstart', handleOutsideClick);
    }
});