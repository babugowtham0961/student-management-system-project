// script.js

let students = [];

window.onload = () => {
    loadStudents();
};

function openModal() {
    document.getElementById("studentModal").style.display = "flex";
}

function closeModal() {
    document.getElementById("studentModal").style.display = "none";

    document.getElementById("studentId").value = "";
    document.getElementById("name").value = "";
    document.getElementById("age").value = "";
    document.getElementById("course").value = "";
}

function saveStudent() {

    const id = document.getElementById("studentId").value;
    const name = document.getElementById("name").value;
    const age = document.getElementById("age").value;
    const course = document.getElementById("course").value;

    const data = new URLSearchParams();
    data.append("id", id);
    data.append("name", name);
    data.append("age", age);
    data.append("course", course);

    fetch("StudentServlet", {
        method: "POST",
        body: data
    })
    .then(res => res.text())
    .then(msg => {
        alert(msg);
        closeModal();
        loadStudents();
    });
}

function loadStudents() {

    fetch("StudentServlet")
    .then(res => res.json())
    .then(data => {

        students = data;

        let rows = "";

        data.forEach(student => {

            rows += `
                <tr>
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student.age}</td>
                    <td>${student.course}</td>

                    <td>
                        <button class="edit-btn"
                            onclick="editStudent(${student.id}, '${student.name}', ${student.age}, '${student.course}')">
                            Edit
                        </button>

                        <button class="delete-btn"
                            onclick="deleteStudent(${student.id})">
                            Delete
                        </button>
                    </td>
                </tr>
            `;
        });

        document.getElementById("studentTable").innerHTML = rows;
    });
}

function editStudent(id, name, age, course) {

    openModal();

    document.getElementById("studentId").value = id;
    document.getElementById("name").value = name;
    document.getElementById("age").value = age;
    document.getElementById("course").value = course;
}

function deleteStudent(id) {

    if(confirm("Delete this student?")) {

        fetch("StudentServlet?id=" + id, {
            method: "DELETE"
        })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
            loadStudents();
        });
    }
}

function searchStudent() {

    const keyword = document
        .getElementById("searchInput")
        .value
        .toLowerCase();

    const filtered = students.filter(s =>
        s.name.toLowerCase().includes(keyword)
    );

    let rows = "";

    filtered.forEach(student => {

        rows += `
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.age}</td>
                <td>${student.course}</td>

                <td>
                    <button class="edit-btn"
                        onclick="editStudent(${student.id}, '${student.name}', ${student.age}, '${student.course}')">
                        Edit
                    </button>

                    <button class="delete-btn"
                        onclick="deleteStudent(${student.id})">
                        Delete
                    </button>
                </td>
            </tr>
        `;
    });

    document.getElementById("studentTable").innerHTML = rows;
}