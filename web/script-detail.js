// this file only handles the patient detail screen (room + route)

function openDetail(patient) {
    currentPatient = patient;
    currentLang = "en";

    document.getElementById("detailName").textContent = patient.name;
    document.getElementById("detailBasic").textContent =
        "Age: " + patient.age + " | City: " + patient.city;

    fillDetail(patient);

    dashboardView.classList.add("hidden");
    detailView.classList.remove("hidden");
    document.getElementById("resyncBadge").classList.add("hidden");
}

function fillDetail(patient) {
    document.getElementById("detailRoom").textContent =
        patient.ward + ", " + patient.room + ", " + patient.bed;

    document.getElementById("detailRoute").textContent =
        currentLang === "en" ? patient.routeEn : patient.routeHi;
}

document.getElementById("backBtn").addEventListener("click", function () {
    detailView.classList.add("hidden");
    dashboardView.classList.remove("hidden");
    currentPatient = null;
});

document.getElementById("langEnBtn").addEventListener("click", function () {
    currentLang = "en";
    fillDetail(currentPatient);
});

document.getElementById("langHiBtn").addEventListener("click", function () {
    currentLang = "hi";
    fillDetail(currentPatient);
});
