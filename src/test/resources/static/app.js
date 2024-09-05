const apiUrl = '/api/students';

document.addEventListener('DOMContentLoaded', loadStudents);

function loadStudents() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            const studentTable = document.getElementById('studentTable').getElementsByTagName('tbody')[0];
            studentTable.innerHTML = '';
            data.forEach(student => {
                let row = studentTable.insertRow();
                row.innerHTML = `
                    <td>${student.usn}</td>
                    <td>${student.name}</td>
                    <td>${student.emailId}</td>
                    <td>${student.phone}</td>
                    <td>${student.courses}</td>
                    <td>
                        <button class="btn btn-sm btn-info" onclick="showEditStudentModal('${student.usn}')">Edit</button>
                        <button class="btn btn-sm btn-danger" onclick="deleteStudent('${student.usn}')">Delete</button>
                    </td>
                `;
            });
        });
}

function showAddStudentModal() {
    document.getElementById('studentForm').reset();
    document.getElementById('studentModalLabel').innerText = 'Add Student';
    document.getElementById('studentUsn').value = '';
    $('#studentModal').modal('show');
}

function showEditStudentModal(usn) {
    fetch(`${apiUrl}/usn/${usn}`)
        .then(response => response.json())
        .then(student => {
            document.getElementById('studentUsn').value = student.usn;
            document.getElementById('usnInput').value = student.usn;
            document.getElementById('nameInput').value = student.name;
            document.getElementById('emailInput').value = student.emailId;
            document.getElementById('phoneInput').value = student.phone;
            document.getElementById('coursesInput').value = student.courses;
            document.getElementById('studentModalLabel').innerText = 'Edit Student';
            $('#studentModal').modal('show');
        });
}

document.getElementById('studentForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const studentData = {
        usn: document.getElementById('usnInput').value,
        name: document.getElementById('nameInput').value,
        emailId: document.getElementById('emailInput').value,
        phone: document.getElementById('phoneInput').value,
        courses: document.getElementById('coursesInput').value,
    };

    const usn = document.getElementById('studentUsn').value;
    const method = usn ? 'PUT' : 'POST';
    const url = usn ? `${apiUrl}/usn/${usn}` : apiUrl;

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(studentData)
    })
    .then(response => response.json())
    .then(() => {
        $('#studentModal').modal('hide');
        loadStudents();
    });
});

function deleteStudent(usn) {
    if (confirm('Are you sure you want to delete this student?')) {
        fetch(`${apiUrl}/usn/${usn}`, {
            method: 'DELETE',
        })
        .then(() => {
            loadStudents();
        });
    }
}
