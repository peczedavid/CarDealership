<template>
  <div class="container-fluid bg-light footer">
    <div class="container text-center pt-2">
        <p class="mb-2">Copyright &copy; Dealership, Inc.</p>
        <p class="mb-3"><a href="">Legal stuff</a> | <a href="">Privacy Policy</a> | <a href="">Security</a></p>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
.footer {
    position: fixed;
    bottom: 0%;
    left: 0%;
    border-top: 1px solid #ababab;
    box-shadow: inset 0px 10px 10px #e4e4e4;
}

.footer p {
    font-weight: 500;
    color: #353535;
}

.footer a {
    font-weight: 500;
    color: #353535;
}
.footer a:hover {
    font-weight: 500;
    color: #707070;
}
</style>