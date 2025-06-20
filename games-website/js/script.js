document.addEventListener("DOMContentLoaded", function () {
  // --- Comunity poll ---
  const pollForm = document.getElementById("pollForm");
  if (pollForm) {
    pollForm.addEventListener("submit", function (e) {
      e.preventDefault();
      document.getElementById("pollMessage").style.display = "block";
    });
  }

  // --- Reviews ---
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

  // --- "Thank You" + snowfall animation ---
  
    const thankYou = document.getElementById("thank-you");
    const canvas = document.getElementById("snow-canvas");
  
    if (!thankYou || !canvas) return;
  
    const ctx = canvas.getContext("2d");
    let animationId = null;
    let currentSeason = "winter"; // Base
    let snowflakes = [];
    let sakuraPetals = [];
  
    function resizeCanvas() {
      canvas.width = canvas.offsetWidth;
      canvas.height = canvas.offsetHeight;
    }
  
    window.addEventListener("resize", resizeCanvas);
    resizeCanvas();
  
    function stopAnimation() {
      if (animationId) {
        cancelAnimationFrame(animationId);
        animationId = null;
      }

      // Remove a canvas and items
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      sakuraPetals = [];
      snowflakes = [];
    }
  
    // --- Snow animation ---
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
  
    function snowLoop() {
      drawSnowflakes();
      updateSnowflakes();
      animationId = requestAnimationFrame(snowLoop);
    }
  
    // --- Sakura animation ---
    function createPetal() {
      return {
        x: Math.random() * canvas.width,
        y: Math.random() * canvas.height,
        scale: 0,
        maxScale: Math.random() * 0.3 + 0.7,
        opacity: 0,
        growing: true,
        life: 0,
        maxLife: 200 + Math.random() * 100,
        rotation: Math.random() * 360
      };
    }
    
    function drawPetal(petal) {
      ctx.save();
      ctx.translate(petal.x, petal.y);
      ctx.rotate((petal.rotation * Math.PI) / 180);
      ctx.scale(petal.scale, petal.scale); 
      ctx.globalAlpha = petal.opacity;
    
      const numberOfPetal = 5;
      const radius = 20;
      for (let i = 0; i < numberOfPetal; i++) {
        const angle = (i * 2 * Math.PI) / numberOfPetal;
        const x = Math.cos(angle) * radius;
        const y = Math.sin(angle) * radius;
        
        ctx.beginPath();
        ctx.ellipse(x, y, 10, 20, angle, 0, Math.PI * 2);
        ctx.fillStyle = "rgba(255, 182, 193, 0.9)";
        ctx.fill();
      }
    
      ctx.beginPath();
      ctx.arc(0, 0, 5, 0, Math.PI * 2);
      ctx.fillStyle = "rgba(60, 120, 60, 0.8)";
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
      for (let i = sakuraPetals.length - 1; i >= 0; i--) {
        const petal = sakuraPetals[i];
        petal.life++;
    
        if (petal.growing) {
          petal.scale += 0.01;
          petal.opacity += 0.02;
          if (petal.scale >= petal.maxScale) {
            petal.growing = false;
          }
        } else {
          petal.scale -= 0.005;
          petal.opacity -= 0.01;
        }
    
        if (petal.life > petal.maxLife || petal.opacity <= 0) {
          sakuraPetals.splice(i, 1);
        }
      }
    
      // Automatic flowers rendering
      if (sakuraPetals.length < 25) {
        sakuraPetals.push(createPetal());
      }
    }
    
    function sakuraLoop() {
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      drawPetals();
      updatePetals();
      animationId = requestAnimationFrame(sakuraLoop);
    }
  
    // --- Button: Start animation ---
    thankYou.addEventListener("click", function () {
      stopAnimation();
  
      if (currentSeason === "winter") {
        snowflakes = Array.from({ length: 100 }, createSnowflake);
        snowLoop();
      } else if (currentSeason === "spring") {
        sakuraPetals = Array.from({ length: 10 }, createPetal);
        sakuraLoop();
      }
  
      thankYou.classList.toggle("frozen");
    });
  });
  
