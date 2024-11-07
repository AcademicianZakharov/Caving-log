function searchTable() {
    const input = document.getElementById('searchInput').value.toLowerCase();
    const rows = document.querySelectorAll('#caversTable tbody tr');
    let hasVisibleRows = false;
    rows.forEach(row => {
        const caveName = row.cells[2].textContent.toLowerCase();

        const rowText = caveName;
        if (rowText.includes(input)) {
            row.style.display = '';
            hasVisibleRows = true;
        } else {
            row.style.display = 'none';
        }
    });
    const noResultsMsg = document.querySelector('.no-results');
    if (!hasVisibleRows && input !== '') {
        if (!noResultsMsg) {
            const msg = document.createElement('p');
            msg.className = 'no-results';
            msg.textContent = 'No matching records found';
            document.querySelector('.read-container').appendChild(msg);
        }
        noResultsMsg.style.display = 'block';
    } else if (noResultsMsg) {
        noResultsMsg.style.display = 'none';
    }
}