function searchTable() {
	//takes input from search bar makes it lowercase
    const input = document.getElementById('searchInput').value.toLowerCase();
	//selects rows
    const rows = document.querySelectorAll('#caversTable tbody tr');
    let hasVisibleRows = false;
	//loops through rows and concatenates the cloumns, then checks if the coloumns contain the input and sets hasVisibleRows = true if it does
    rows.forEach(row => {
        const name = row.cells[1].textContent.toLowerCase();
        const phone = row.cells[2].textContent.toLowerCase();
        const status = row.cells[3].textContent.toLowerCase();
        const rowText = name + phone + status;
        if (rowText.includes(input)) {
            row.style.display = '';
            hasVisibleRows = true;
        } else {
            row.style.display = 'none';
        }
    });
	//ouputs message Not found if the input doesnt match
    const noResultsMsg = document.querySelector('.no-results');
    if (!hasVisibleRows && input !== '') {
        if (!noResultsMsg) {
            const msg = document.createElement('p');
            msg.className = 'no-results';
            msg.textContent = 'Not found';
            document.querySelector('.read-container').appendChild(msg);
        }
        noResultsMsg.style.display = 'block';
    } else if (noResultsMsg) {
        noResultsMsg.style.display = 'none';
    }
}