# frozen_string_literal: true

require "test_helper"

class TestAdvent23 < Minitest::Test
  def test_that_it_has_a_version_number
    refute_nil ::Advent23::VERSION
  end

  def test_it_does_something_useful
    assert 2 + 2 == 4
  end
end
