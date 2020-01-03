export class StatusFilter {
  inProgress: boolean;
  new: boolean;
  valid: boolean;
  locked: boolean;
  requiresRegeneration: boolean;
  failed: boolean;
  constructor() {
    this.inProgress = true;
    this.new = true;
    this.valid = true;
    this.locked = true;
    this.requiresRegeneration = true;
    this.failed = true;
  }
}
