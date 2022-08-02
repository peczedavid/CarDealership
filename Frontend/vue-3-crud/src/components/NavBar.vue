<template>
  <nav class="navbar navbar-expand-sm navbar-dark bg-primary">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">Dealership</a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="#">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Cars</a>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <button type="button" class="btn btn-outline-light me-2">
              Log in
            </button>
          </li>
          <li class="nav-item">
            <button type="button" class="btn btn-outline-light">
              Register
            </button>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      user: {
        username: '', // Blank if not logged in
        admin: false
      }
    }
  }
};
</script>

<style></style>
