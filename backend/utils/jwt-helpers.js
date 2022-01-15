import jwt from 'jsonwebtoken';

//Generate an access token and a refresh token for this database user
function jwtTokens({ id_user, username, invite_code, avatar_url }) {
  const user = { id_user, username, invite_code, avatar_url }; 
  const accessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET, { expiresIn: '1000s' });
  return ({ accessToken });
}

export {jwtTokens};
