const form = document.getElementById("resumeForm");
const fileInput = document.getElementById("resumeFile");
const resultDiv = document.getElementById("result");
const scoreEl = document.getElementById("score");
const matchedEl = document.getElementById("matched");
const totalEl = document.getElementById("total");
const missingList = document.getElementById("missingList");
const scoreBar = document.getElementById("scoreBar");

// Drag & Drop support
form.addEventListener("dragover", (e) => {
  e.preventDefault();
  fileInput.style.borderColor = "#6C63FF";
});

form.addEventListener("dragleave", (e) => {
  e.preventDefault();
  fileInput.style.borderColor = "#ccc";
});

form.addEventListener("drop", (e) => {
  e.preventDefault();
  fileInput.files = e.dataTransfer.files;
  fileInput.style.borderColor = "#6C63FF";
});

// Submit form
form.addEventListener("submit", async (e) => {
  e.preventDefault();
  const jobDescription = document.getElementById("jobDescription").value;

  if (!fileInput.files.length) {
    alert("Please upload a resume file!");
    return;
  }

  const formData = new FormData();
  formData.append("file", fileInput.files[0]);
  formData.append("jobDescription", jobDescription);

  try {
    const res = await fetch("http://localhost:9090/api/resume/upload", {
      method: "POST",
      body: formData,
    });

    if (!res.ok) throw new Error("Server error");

    const data = await res.json();

    // Display results
    scoreEl.textContent = data.score.toFixed(2);
    matchedEl.textContent = data.matchedKeywords;
    totalEl.textContent = data.totalKeywords;

    scoreBar.style.width = "0%";
    setTimeout(() => {
      scoreBar.style.width = `${data.score.toFixed(2)}%`;
    }, 100);

    missingList.innerHTML = "";
    if (data.missingKeywords && data.missingKeywords.length > 0) {
      data.missingKeywords.forEach((keyword) => {
        const li = document.createElement("li");
        li.textContent = keyword;
        missingList.appendChild(li);
      });
    } else {
      missingList.innerHTML = "<li>None</li>";
    }

    resultDiv.classList.remove("hidden");
  } catch (err) {
    alert("Error analyzing resume: " + err.message);
    console.error(err);
  }
});
