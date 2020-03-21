export class Login {

  private readonly user: string;
  private readonly pwd: string;

  constructor(user: string,
              pwd: string) {
    this.user = user;
    this.pwd = pwd;
  }
}
