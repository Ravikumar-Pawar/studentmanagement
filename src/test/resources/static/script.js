document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('studentForm');
    const studentTable = document.getElementById('studentTable').getElementsByTagName('tbody')[0];
    const submitButton = document.getElementById('submitButton');
    let students = [];

    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const id = document.getElementById('studentId').value;
        const usn = document.getElementById('usn').value;
        const name = document.getElementById('name').value;
        const emailId = document.getElementById('emailId').value;
        const phone = document.getElementById('phone').value;
        const courses = document.getElementById('courses').value;

        if (!usn || !name || !emailId || !phone || !courses) {
            alert("All fields are required!");
            return;
        }

        if (id) {
            // Update existing student
            const studentIndex = students.findIndex(s => s.id === parseInt(id));
            if (studentIndex !== -1) {
                students[studentIndex] = { id: parseInt(id), usn, name, emailId, phone, courses };
            }
        } else {
            // Add new student
            const newStudent = {
                id: Date.now(),
                usn,
                name,
                emailId,
                phone,
                courses
            };
            students.push(newStudent);
        }

        form.reset();
        submitButton.textContent = 'Add Student';
        renderTable();
    });

    function renderTable() {
        studentTable.innerHTML = '';
        students.forEach(student => {
            const row = studentTable.insertRow();
            row.insertCell(0).textContent = student.usn;
            row.insertCell(1).textContent = student.name;
            row.insertCell(2).textContent = student.emailId;
            row.insertCell(3).textContent = student.phone;
            row.insertCell(4).textContent = student.courses;

            const actionsCell = row.insertCell(5);
            const editButton = document.createElement('button');
            editButton.textContent = 'Edit';
            editButton.className = 'btn btn-warning btn-sm me-2';
            editButton.onclick = () => editStudent(student.id);

            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.className = 'btn btn-danger btn-sm';
            deleteButton.onclick = () => deleteStudent(student.id);

            actionsCell.appendChild(editButton);
            actionsCell.appendChild(deleteButton);
        });
    }

    function editStudent(id) {
        const student = students.find(s => s.id === id);
        if (student) {
            document.getElementById('studentId').value = student.id;
            document.getElementById('usn').value = student.usn;
            document.getElementById('name').value = student.name;
            document.getElementById('emailId').value = student.emailId;
            document.getElementById('phone').value = student.phone;
            document.getElementById('courses').value = student.courses;
            submitButton.textContent = 'Update Student';
        }
    }

    function deleteStudent(id) {
        const confirmDelete = confirm("Are you sure you want to delete this student?");
        if (confirmDelete) {
            students = students.filter(s => s.id !== id);
            renderTable();
        }
    }
});
