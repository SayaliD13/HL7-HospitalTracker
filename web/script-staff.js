// this file only handles staff-only actions

document.getElementById("staffOpenBtn").addEventListener("click", function () {
    staffLoginBox.classList.toggle("hidden");
});

document.getElementById("staffLoginBtn").addEventListener("click", function () {
    const name = document.getElementById("staffName").value;
    const id = document.getElementById("staffId").value;

    const formData = new URLSearchParams();
    formData.append("name", name);
    formData.append("id", id);

    fetch("staffLogin", { method: "POST", body: formData })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                staffLoginBox.classList.add("hidden");
                staffPanel.classList.remove("hidden");
                loadStaffPatientList();
            } else {
                document.getElementById("staffLoginMsg").textContent = "Wrong name or ID";
            }
        });
});

function loadStaffPatientList() {
    fetch("search?name=")
        .then(res => res.json())
        .then(data => {
            const body = document.getElementById("staffPatientBody");
            body.innerHTML = "";

            data.forEach(p => {
                const row = document.createElement("tr");
                row.innerHTML =
                    "<td class='p-2'>" + p.name + "</td>" +
                    "<td class='p-2'>" + p.ward + "</td>" +
                    "<td class='p-2'>" +
                    "<button class='text-amber-400 underline mr-2 shift-btn'>Simulate ADT Shift</button>" +
                    "<button class='text-red-400 underline delete-btn'>Delete</button>" +
                    "</td>";

                row.querySelector(".shift-btn").addEventListener("click", () => shiftPatient(p.id));
                row.querySelector(".delete-btn").addEventListener("click", () => deletePatient(p.id));

                body.appendChild(row);
            });
        });
}

document.getElementById("addPatientBtn").addEventListener("click", function () {
    const formData = new URLSearchParams();
    formData.append("name", document.getElementById("addName").value);
    formData.append("age", document.getElementById("addAge").value);
    formData.append("city", document.getElementById("addCity").value);
    formData.append("ward", document.getElementById("addWard").value);

    fetch("addPatient", { method: "POST", body: formData })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                loadStaffPatientList();
            }
        });
});

function deletePatient(id) {
    const formData = new URLSearchParams();
    formData.append("id", id);

    fetch("deletePatient", { method: "POST", body: formData })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                loadStaffPatientList();
            }
        });
}

function shiftPatient(id) {
    const formData = new URLSearchParams();
    formData.append("id", id);

    fetch("shiftRoom", { method: "POST", body: formData })
        .then(res => res.json())
        .then(data => {
            if (!data.success) return;

            loadStaffPatientList();

            // if the visitor is currently looking at this same patient,
            // update the screen right away without a page reload
            if (currentPatient && currentPatient.id === id) {
                currentPatient.ward = data.ward;
                currentPatient.room = data.room;
                currentPatient.bed = data.bed;
                currentPatient.routeEn = data.routeEn;
                currentPatient.routeHi = data.routeHi;

                fillDetail(currentPatient);
                document.getElementById("resyncBadge").classList.remove("hidden");
            }
        });
}
