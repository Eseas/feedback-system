function showFirstInvalidTab() {
    var errorTab = $('div.ui-tabs-panel:has(.ui-state-error)').attr('id');
    if (errorTab) {
        var expression = 'a[href="#' + errorTab + '"]';
        var link = $(expression);
        if (link) {
            link.click();
        }
    }
}