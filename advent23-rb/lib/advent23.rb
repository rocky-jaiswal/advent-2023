# frozen_string_literal: true

require_relative "advent23/version"
require_relative "advent23/day1"

module Advent23
  class Error < StandardError; end
  class App
    def main
      puts "Ho Ho Ho!"
    end

    def day1
      ::Advent23::Day1.new.main
    end
  end
end
