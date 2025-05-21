document.addEventListener("DOMContentLoaded", function () {
  // --- Szavazás űrlap ---
  const pollForm = document.getElementById("pollForm");
  if (pollForm) {
    pollForm.addEventListener("submit", function (e) {
      e.preventDefault();
      document.getElementById("pollMessage").style.display = "block";
    });
  }

  // --- Vélemények (Reviews) ---
  const reviewForm = document.getElementById("reviewForm");
  const reviewList = document.getElementById("reviewList");

  function loadReviews() {
    const reviews = JSON.parse(localStorage.getItem("reviews") || "[]");
    reviewList.innerHTML = "";
    reviews.forEach((review, index) => {
      const div = document.createElement("div");
      div.className = "card mb-3 p-3";

      div.innerHTML = `
        <div class="position-relative">
          <button class="btn btn-sm btn-outline-danger position-absolute top-0 end-0" data-delete="${index}" title="Delete">
            🗑️
          </button>
          <div class="d-flex align-items-center mb-1">
            <h5 class="review-name mb-0 me-2">${review.name}</h5>
            <div class="rating" data-index="${index}">
              ${[1, 2, 3, 4, 5].map(star =>
                `<span class="star" data-star="${star}">☆</span>`
              ).join('')}
            </div>
          </div>
          <p class="review-text mb-0">${review.text}</p>
        </div>
      `;

      reviewList.appendChild(div);
      updateRatingDisplay(div.querySelector(".rating"), review.rating || 0);
    });
  }

  function updateRatingDisplay(ratingDiv, rating) {
    const stars = ratingDiv.querySelectorAll(".star");
    stars.forEach(star => {
      const starValue = parseInt(star.dataset.star);
      star.textContent = starValue <= rating ? "★" : "☆";
    });
  }

  if (reviewList) {
    reviewList.addEventListener("click", function (e) {
      if (e.target.classList.contains("star")) {
        const ratingDiv = e.target.closest(".rating");
        const reviewIndex = ratingDiv.dataset.index;
        const rating = parseInt(e.target.dataset.star);

        const reviews = JSON.parse(localStorage.getItem("reviews") || "[]");
        reviews[reviewIndex].rating = rating;
        localStorage.setItem("reviews", JSON.stringify(reviews));
        updateRatingDisplay(ratingDiv, rating);
      }

      if (e.target.dataset.delete !== undefined) {
        const index = parseInt(e.target.dataset.delete);
        const reviews = JSON.parse(localStorage.getItem("reviews") || "[]");
        reviews.splice(index, 1);
        localStorage.setItem("reviews", JSON.stringify(reviews));
        loadReviews();
      }
    });
  }

  if (reviewForm) {
    reviewForm.addEventListener("submit", function (e) {
      e.preventDefault();
      const name = document.getElementById("reviewerName").value;
      const text = document.getElementById("reviewText").value;

      const reviews = JSON.parse(localStorage.getItem("reviews") || "[]");
      reviews.push({ name, text, rating: 0 });
      localStorage.setItem("reviews", JSON.stringify(reviews));

      reviewForm.reset();
      loadReviews();
    });

    loadReviews();
  }

  // --- "Thank You" + hóesés animáció ---
  const thankYou = document.getElementById("thank-you");
  const canvas = document.getElementById("snow-canvas");

  if (thankYou && canvas) {
    const ctx = canvas.getContext("2d");
    let snowflakes = [];

    function resizeCanvas() {
      canvas.width = canvas.offsetWidth;
      canvas.height = canvas.offsetHeight;
    }

    window.addEventListener("resize", resizeCanvas);
    resizeCanvas();

    function createSnowflake() {
      return {
        x: Math.random() * canvas.width,
        y: Math.random() * -canvas.height,
        radius: Math.random() * 3 + 1,
        speed: Math.random() * 1 + 0.5,
        drift: Math.random() * 0.6 - 0.3
      };
    }

    function drawSnowflakes() {
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      for (let flake of snowflakes) {
        ctx.beginPath();
        ctx.arc(flake.x, flake.y, flake.radius, 0, Math.PI * 2);
        ctx.fillStyle = "white";
        ctx.fill();
      }
    }

    function updateSnowflakes() {
      for (let flake of snowflakes) {
        flake.y += flake.speed;
        flake.x += flake.drift;
        if (flake.y > canvas.height) {
          Object.assign(flake, createSnowflake());
          flake.y = 0;
        }
      }
    }

    function loop() {
      drawSnowflakes();
      updateSnowflakes();
      requestAnimationFrame(loop);
    }

    thankYou.addEventListener("click", function () {
      thankYou.classList.toggle("frozen");
      snowflakes = Array.from({ length: 100 }, createSnowflake);
      loop();
    });
  }

  // --- Évszak váltás ---

  const dropbtn = document.getElementById('dropdownMenuButton');
  const dropdownMenu = document.querySelector('.dropdown-menu');
  const seasonButtons = document.querySelectorAll('.season-option');
  seasonButtons.forEach(button => {
    button.addEventListener('click', () => {
      const season = button.dataset.season;
      document.body.className = '';
      document.body.classList.add(season);

      // Bootstrap dropdown bezárása
      const dropdown = bootstrap.Dropdown.getInstance(dropbtn);
      if (dropdown) dropdown.hide();
    });
  });

  // --- Sakura virág hullás animáció (spring témához) ---
if (thankYou && canvas) {
  const ctx = canvas.getContext("2d");
  let sakuraPetals = [];

  function resizeCanvas() {
    canvas.width = canvas.offsetWidth;
    canvas.height = canvas.offsetHeight;
  }

  window.addEventListener("resize", resizeCanvas);
  resizeCanvas();

  function createPetal() {
    return {
      x: Math.random() * canvas.width,
      y: Math.random() * -canvas.height,
      radius: Math.random() * 6 + 4,
      speed: Math.random() * 1 + 0.5,
      drift: Math.random() * 0.8 - 0.4,
      rotation: Math.random() * 360,
      rotationSpeed: (Math.random() - 0.5) * 2
    };
  }

  function drawPetal(petal) {
    ctx.save();
    ctx.translate(petal.x, petal.y);
    ctx.rotate((petal.rotation * Math.PI) / 180);
    // Egyszerű virág alakzat (5 szirmú)
    ctx.fillStyle = "rgba(255, 182, 193, 0.8)"; // halvány rózsaszín
    ctx.beginPath();
    for (let i = 0; i < 5; i++) {
      const angle = (i * 2 * Math.PI) / 5;
      const x = Math.cos(angle) * petal.radius;
      const y = Math.sin(angle) * petal.radius;
      ctx.lineTo(x, y);
    }
    ctx.closePath();
    ctx.fill();
    ctx.restore();
  }

  function drawPetals() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    for (let petal of sakuraPetals) {
      drawPetal(petal);
    }
  }

  function updatePetals() {
    for (let petal of sakuraPetals) {
      petal.y += petal.speed;
      petal.x += petal.drift;
      petal.rotation += petal.rotationSpeed;
      if (petal.y > canvas.height) {
        Object.assign(petal, createPetal());
        petal.y = 0;
      }
      if (petal.x > canvas.width) petal.x = 0;
      if (petal.x < 0) petal.x = canvas.width;
    }
  }

  function loop() {
    drawPetals();
    updatePetals();
    requestAnimationFrame(loop);
  }

  thankYou.addEventListener("click", function () {
    thankYou.classList.toggle("frozen");
    if (thankYou.classList.contains("frozen")) {
      sakuraPetals = Array.from({ length: 80 }, createPetal);
      loop();
    }
  });
}


});
