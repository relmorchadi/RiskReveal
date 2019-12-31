export class StatusFilter {
  inProgress: boolean;
  new: boolean;
  valid: boolean;
  locked: boolean;
  requiresRegeneration: boolean;
  failed: boolean;
  constructor() {
    this.inProgress = false;
    this.new = false;
    this.valid = false;
    this.locked = false;
    this.requiresRegeneration = false;
    this.failed = false;
  }
}
