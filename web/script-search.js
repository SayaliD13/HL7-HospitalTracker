// this file only handles the search bar and the results table

document.getElementById("searchBtn").addEventListener("click", function () {
    const name = document.getElementById("searchBox").value.trim();
    fetch("search?name=" + encodeURIComponent(name))
        .then(res => res.json())
        .then(data => showResults(data));
});

function showResults(patients) {
    const table = document.getElementById("resultTable");
    const body = document.getElementById("resultBody");
    const message = document.getElementById("searchMessage");

    body.innerHTML = "";

    if (patients.length === 0) {
        table.classList.add("hidden");
        message.textContent = "No patient found with this name.";
        return;
    }

    message.textContent = "";
    table.classList.remove("hidden");

    patients.forEach(p => {
        const row = document.createElement("tr");
        row.innerHTML =
            "<td class='p-2'>" + p.name + "</td>" +
            "<td class='p-2'>" + p.age + "</td>" +
            "<td class='p-2'>" + p.city + "</td>" +
            "<td class='p-2'>" + p.ward + "</td>" +
            "<td class='p-2'><button class='text-amber-400 underline view-btn'>View Details</button></td>";

        row.querySelector(".view-btn").addEventListener("click", () => openDetail(p));
        body.appendChild(row);
    });
}
