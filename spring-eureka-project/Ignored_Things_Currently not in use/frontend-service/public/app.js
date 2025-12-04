// Legacy simple register (kept for backward compatibility)
async function registerUserLegacy() {
  const msg = document.getElementById('msg');
  if(!msg) return;
  msg.textContent = 'Please wait...';
  const payload = {
    name: document.getElementById('name').value,
    email: document.getElementById('email').value,
    password: document.getElementById('password').value
  };
  try {
    const res = await fetch('/proxy/users/add', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (res.ok) {
      msg.textContent = 'Registration successful.';
    } else {
      msg.textContent = 'Registration failed.';
    }
  } catch (err) {
    msg.textContent = 'Error: ' + err.message;
  }
}
